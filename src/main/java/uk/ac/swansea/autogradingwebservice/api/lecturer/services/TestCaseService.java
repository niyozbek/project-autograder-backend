package uk.ac.swansea.autogradingwebservice.api.lecturer.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.lecturer.controllers.dto.TestCaseDto;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.Problem;
import uk.ac.swansea.autogradingwebservice.api.lecturer.entities.TestCase;
import uk.ac.swansea.autogradingwebservice.api.lecturer.repositories.TestCaseRepository;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class TestCaseService {
    @Autowired
    private TestCaseRepository testCaseRepository;
    @Autowired
    private ProblemService problemService;

    public List<TestCase> getAllTestCasesByProblemId(Long id) {
        return testCaseRepository.findAllByProblemId(id);
    }
    public List<TestCase> getAllTestCasesByProblemId(Long id, Pageable pageable) {
        return testCaseRepository.findAllByProblemId(id, pageable);
    }

    public TestCase addTestCase(TestCaseDto testCaseDto, Long lecturerId) throws ResourceNotFoundException, BadRequestException {
        Problem problem = problemService.getProblemByLecturerId(testCaseDto.getProblemId(), lecturerId);

        TestCase testCase = new TestCase();
        testCase.setProblemId(problem.getId());
        testCase.setInput(testCaseDto.getInput());
        testCase.setExpectedOutput(testCaseDto.getExpectedOutput());
        return testCaseRepository.save(testCase);
    }

    public int countAllTestCasesByProblemId(Long problemId) {
        return testCaseRepository.countAllByProblemId(problemId);
    }

    public TestCase getFirstTestCaseByProblemId(Long problemId) throws ResourceNotFoundException {
        return testCaseRepository.findFirstByProblemId(problemId)
                .orElseThrow(ResourceNotFoundException::new);
    }
}
