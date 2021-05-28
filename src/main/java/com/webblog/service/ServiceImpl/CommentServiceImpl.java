package com.webblog.service.ServiceImpl;

import com.webblog.POJO.CommentMapper;
import com.webblog.model.Comment;
import com.webblog.model.Person;
import com.webblog.model.Post;
import com.webblog.repository.CommentRepository;
import com.webblog.repository.PersonRepository;
import com.webblog.repository.PostRepository;
import com.webblog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PersonRepository personRepository;

    /**
     * CREATE operation on Comment
     * @param comment
     * @param postId
     * @param userId
     * @return boolean
     * */
    public boolean createComment(Long userId, Long postId, String comment){
        boolean result = false;

        try{
            Person person = personRepository.findById(userId).get();
            Post post = postRepository.findById(postId).get();

            Comment commentData = new Comment();
            //set the post
            commentData.setPost(post);
            commentData.setPerson(person);
            commentData.setComment(comment);

            commentRepository.save(commentData);
            result = true;

        }catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }

    /**
     * GET operation on Comment
     * @param postId
     * @return List of comments
     * */
    public List<CommentMapper> getCommentsByPostId(Long postId){
        List<CommentMapper> comments = new ArrayList();

        try{

            List<Comment> commentsData = commentRepository.findAllByPostId(postId);

            commentsData.forEach(commentEach -> {
                CommentMapper comment = new CommentMapper();

                //comment
                comment.setId(commentEach.getId());
                comment.setComment(commentEach.getComment());

                //post in which comment is made on
                comment.setPostId(commentEach.getPost().getId());
                comment.setTitle(commentEach.getPost().getTitle());
                comment.setImage(commentEach.getPost().getImage());

                //person who made the comment
                comment.setUsername(commentEach.getPerson().getUsername());
                comment.setName(commentEach.getPerson().getName());
                comment.setUserId(commentEach.getPerson().getId());

                comments.add(comment);
            });
        }catch (Exception e){
            e.printStackTrace();
        }

        return comments;
    }

    /**
     * CREATE operation on Comment
     * @param comment
     * @param postId
     * @param commentId
     * @param person
     * @return boolean(true for successful creation and false on failure on comment update)
     * */
    public boolean editComment(Long commentId, Person person, Long postId, String comment) {
        boolean status = false;

        System.out.println("here "+commentId);

        Comment data = commentRepository.findById(commentId).get();

        try {
            Post post = postRepository.findById(postId).get();

            Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

            data.setComment(comment);
            data.setPerson(person1);
            data.setPost(post);

            commentRepository.save(data);

            status = true;

        }catch (Exception e) {
            e.printStackTrace();
        }

        return status;
    }

    /**
     * DELETE operation on Comment
     * @param commentId
     * @return boolean(true for successful deletion and false on failure to delete)
     * */
    public boolean deleteComment(Long commentId){
        boolean status =  false;

        try {
            commentRepository.deleteById(commentId);
            status = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return status;
    }

}
