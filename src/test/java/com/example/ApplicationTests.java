package com.example;

import com.example.data.Sample;
import com.example.data.SampleRepository;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTests {

    @Autowired
    SampleRepository repository;

    @Test
    public void nonEmptyIn() {
        repository.queryIn(Arrays.asList(1, 2, 3));
    }

    @Test
    public void emptySpecIn() {
        List<Sample> result = findIn(Collections.emptyList());
        assertThat(result, hasSize(4));
    }

    @Test
    public void nonEmptySpecIn() {
        List<Integer> ids = Arrays.asList(1, 2, 3);
        List<Sample> result = findIn(ids);
        assertThat(result.stream()
                        .map(sample -> sample.id)
                        .collect(toList()),
                equalTo(ids));
    }

    private List<Sample> findIn(List<Integer> ids) {
        return repository.findAll((root, criteriaQuery, criteriaBuilder) -> {
            if (ids.isEmpty()) {
                return null; // or criteriaBuilder.conjunction()
            } else {
                return root.get("id").in(ids);
            }
        });
    }

    @Test
    public void emptyIn() {
        try {
            repository.queryIn(Collections.emptyList());
            fail("InvalidDataAccessResourceUsageException expected");
        } catch (InvalidDataAccessResourceUsageException ex) {
            ex.printStackTrace();
            assertThat(ExceptionUtils.getStackTrace(ex),
                    ExceptionUtils.getRootCauseMessage(ex),
                    containsString("unexpected token: )")
            );
        }
    }

}
