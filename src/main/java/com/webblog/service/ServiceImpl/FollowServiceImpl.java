package com.webblog.service.ServiceImpl;

import com.webblog.POJO.PersonMapper;
import com.webblog.model.Follow;
import com.webblog.model.Person;
import com.webblog.model.Post;
import com.webblog.repository.FollowRepository;
import com.webblog.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FollowServiceImpl {

    FollowRepository followRepository;
    PersonRepository personRepository;

    @Autowired
    public FollowServiceImpl(FollowRepository followRepository, PersonRepository personRepository) {
        this.followRepository = followRepository;
        this.personRepository = personRepository;
    }

    public String followPerson(Long followeeId, Person person){

        String flag = "failed";

        try {
            Person person1 = personRepository.findPersonByEmail(person.getEmail()).get();

            List<Follow> follow = followRepository.findByCurrentUserIdAndAndFollowerId(person1.getId(), followeeId);

            if(follow.size() == 0){
                Follow follow1 = new Follow();
                follow1.setFollowerId(followeeId);
                follow1.setCurrentUserId(person1.getId());

                followRepository.save(follow1);

                flag = "successful followed";
            }else{
                followRepository.deleteById(follow.get(0).getId());

                flag = "successfully unfollowed";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return flag;
    }

    public Object getFollowersById(Long id, Person person){

        List<PersonMapper> followers = new ArrayList<>();

        try {

            Person currentUser = personRepository.findPersonByEmail(person.getEmail()).get();

            if(currentUser.getId() != id) return "user not authorized";
            else{
                List<Follow> data = followRepository.findAllByFollowerId(currentUser.getId());

                data.forEach(each->{
                    PersonMapper personMapper = new PersonMapper();

                    Person follower = personRepository.findById(each.getCurrentUserId()).get();

                    personMapper.setId(follower.getId());
                    personMapper.setName(follower.getName());
                    personMapper.setEmail(follower.getEmail());

                    followers.add(personMapper);
                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return followers;
    }

    public Object getFolloweesByCurrentUserId(Long id, Person person){
        List<PersonMapper> followees = new ArrayList<>();

        try {

            Person currentUser = personRepository.findPersonByEmail(person.getEmail()).get();

            if(currentUser.getId() != id) return "user not authorized";
            else{
                List<Follow> data = followRepository.findAllByCurrentUserId(id);

                data.forEach(each->{
                    PersonMapper personMapper = new PersonMapper();

                    Person follower = personRepository.findById(each.getFollowerId()).get();
                    personMapper.setId(follower.getId());
                    personMapper.setUsername(follower.getUsername());
                    personMapper.setName(follower.getName());
                    personMapper.setEmail(follower.getEmail());

                    followees.add(personMapper);

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return  followees;

    }

}
