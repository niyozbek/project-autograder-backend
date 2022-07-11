package uk.ac.swansea.autogradingwebservice.api.lecturer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.ProblemDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.TestCaseDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;
import uk.ac.swansea.autogradingwebservice.api.lecturer.repositories.ProblemRepository;
import uk.ac.swansea.autogradingwebservice.api.lecturer.repositories.TestCaseRepository;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;

    public List<TestCase> getAllTestCasesByProblemId(Long id) throws ResourceNotFoundException {
        return testCaseRepository.findAllByProblemId(id);
    }

    public TestCase addTestCase(TestCaseDto testCaseDto) {
        TestCase testCase = new TestCase();
        testCase.setProblemId(testCaseDto.getProblemId());
        testCase.setInput(testCaseDto.getInput());
        testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
        return testCaseRepository.save(testCase);
    }
}
