package uk.ac.swansea.autogradingwebservice.api.student.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uk.ac.swansea.autogradingwebservice.api.student.entities.Submission;
import uk.ac.swansea.autogradingwebservice.api.student.repositories.SubmissionRepository;
import uk.ac.swansea.autogradingwebservice.exceptions.BadRequestException;
import uk.ac.swansea.autogradingwebservice.exceptions.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;

@Service
public class SubmissionService {
    @Autowired
    private SubmissionRepository submissionRepository;

    public List<Submission> getSubmissionsByProblemIdAndStudentId(Long problemId, Long studentId,
                                                                  Pageable pageable) {
        return submissionRepository.findAllByProblemIdAndStudentId(problemId, studentId, pageable);
    }

    // use problemId and studentID to verify if submissionId can be accessed
    public Submission getSubmission(Long submissionId, Long studentId) throws BadRequestException, ResourceNotFoundException {
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(
                ResourceNotFoundException::new);
        if (!Objects.equals(submission.getStudentId(), studentId)) {
            throw new BadRequestException();
        }
        return submission;
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
}
