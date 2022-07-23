package uk.ac.swansea.autogradingwebservice.api.student.services;

import com.github.codeboy.piston4j.api.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.ExecutionDto;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.ExecutionResultDto;
import uk.ac.swansea.autogradingwebservice.api.student.services.dto.RuntimeDto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ExecutionService {
    @Autowired
    private ModelMapper modelMapper;

    /**
     * You can also execute the code without getting the runtime.
     * However, this is not recommended since it won't work unless you know the correct version of the runtime
     *
     * @param dto input
     * @return result
     */
    public ExecutionResultDto submit(ExecutionDto dto) {
        Piston api = Piston.getDefaultApi(); //get the api at https://emkc.org/api/v2/piston
        CodeFile codeFile = new CodeFile(dto.getFilename(), dto.getCode()); //create the codeFile containing the javascript code
        ExecutionRequest request = new ExecutionRequest(dto.getLanguage(), dto.getVersion(), codeFile); //create the request using the codeFile, a language and a version
        request.setStdin(dto.getInput());
        // loop until we get appropriate result, because java code fails sometimes in remote server
        ExecutionOutput executionOutput;
        do {
            ExecutionResult result = api.execute(request); //execute the request
            executionOutput = result.getOutput();
        } while (executionOutput.getCode() != 0 && executionOutput.getSignal() != null);

        String output = executionOutput.getOutput();
        // remove \n from the result
        if (output.length() > 1) {
            output = output.substring(0, output.length() - 1);
        }

        String expectedOutput = dto.getExpectedOutput();
        // populate dto fields
        return ExecutionResultDto.builder()
                .output(output)
                .expectedOutput(expectedOutput)
                .isValid(Objects.equals(output, expectedOutput)).build();
    }

    /**
     * Get list of available runtimes/languages and versions
     *
     * @return list of runtime
     */
    @Cacheable(value = "runtimes")
    public List<RuntimeDto> getRuntimes() {
        Piston api = Piston.getDefaultApi(); //get the api at https://emkc.org/api/v2/piston
        return api.getRuntimes()
                .stream()
                .map(element -> modelMapper.map(element, RuntimeDto.class))
                .collect(Collectors.toList());
    }
}
