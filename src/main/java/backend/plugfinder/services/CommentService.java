package backend.plugfinder.services;

import backend.plugfinder.repositories.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    CommentRepo comment_repo;
}
