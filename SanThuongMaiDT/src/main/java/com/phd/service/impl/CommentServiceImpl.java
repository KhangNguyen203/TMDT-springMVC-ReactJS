package com.phd.service.impl;

import com.phd.pojo.Comments;
import com.phd.pojo.User;
import com.phd.repository.CommentRepository;
import com.phd.repository.UserRepository;
import com.phd.service.CommentService;
import java.util.List;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author 84355
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepo;
    @Autowired
    private UserRepository userRepo;

    @Override
    public List<Comments> getComments(int productId) {
        return this.commentRepo.getComments(productId);
    }

    @Override
    public Comments addComment(Comments c) {
        c.setCreatedAt(new Date());

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User u = this.userRepo.getUserByUsername(authentication.getName());
        c.setUserId(u);

        return this.commentRepo.addComment(c);
    }

}
