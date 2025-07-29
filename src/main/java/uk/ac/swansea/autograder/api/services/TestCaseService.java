package uk.ac.swansea.autograder.api.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.TestCaseDto;
import uk.ac.swansea.autograder.api.entities.TestCase;
import uk.ac.swansea.autograder.api.repositories.TestCaseRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class TestCaseService {
    private final TestCaseRepository testCaseRepository;

    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public List<TestCase> getAllTestCasesByProblemId(Long id) {
        return testCaseRepository.findAllByProblemId(id);
    }
    public List<TestCase> getAllTestCasesByProblemId(Long id, Pageable pageable) {
        return testCaseRepository.findAllByProblemId(id, pageable);
    }

    public TestCase addTestCase(Long problemId, TestCaseDto testCaseDto) {
        TestCase testCase = new TestCase();
        testCase.setProblemId(problemId);
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

    public List<TestCase> getAllTestCases(Pageable pageable) {
        return testCaseRepository.findAll(pageable).toList();
    }
}
