package uk.ac.swansea.autograder.api.services;

import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autograder.api.entities.Submission;
import uk.ac.swansea.autograder.api.repositories.SubmissionRepository;
import uk.ac.swansea.autograder.exceptions.ResourceNotFoundException;

import java.util.List;

@Service
public class SubmissionService {
    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    public List<Submission> getSubmissionsByProblemIdAndStudentId(Long problemId, Long studentId,
                                                                  Pageable pageable) {
        return submissionRepository.findAllByProblemIdAndStudentId(problemId, studentId, pageable);
    }

    public Submission getSubmission(Long submissionId) throws ResourceNotFoundException {
        return submissionRepository.findById(submissionId).orElseThrow(
                ResourceNotFoundException::new);
    }

    public List<Submission> getSubmissionsByStudentId(Long studentId, Pageable pageable) {
        return submissionRepository.findAllByStudentId(studentId, pageable);
    }

    public List<Submission> getSubmissionsByProblemId(Long problemId, Pageable pageable) {
        return submissionRepository.findAllByProblemId(problemId, pageable);
    }

    public Submission createSubmission(Long problemId, Long studentId, String language, String version, String filename, String code) {
        Submission submission = new Submission();
        submission.setProblemId(problemId);
        submission.setStudentId(studentId);
        submission.setLanguage(language);
        submission.setVersion(version);
        submission.setFilename(filename);
        submission.setCode(code);
        submission.setStatus(Submission.Status.NEW);
        return submissionRepository.save(submission);
    }

    public void updateSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    public List<Submission> getSubmissions(Pageable pageable) {
        return submissionRepository.findAll(pageable).toList();
    }
}
