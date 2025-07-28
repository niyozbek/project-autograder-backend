package uk.ac.swansea.autograder.general.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.controllers.dto.TestCaseDto;
import uk.ac.swansea.autograder.general.entities.Problem;
import uk.ac.swansea.autograder.general.entities.TestCase;
import uk.ac.swansea.autograder.general.repositories.TestCaseRepository;
import uk.ac.swansea.autograder.exceptions.BadRequestException;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

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

    public TestCase addTestCase(Long problemId, TestCaseDto testCaseDto, Long lecturerId) throws ResourceNotFoundException, BadRequestException {
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
