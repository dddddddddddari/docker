package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    private final static Logger LOGGER = LoggerFactory.getLogger(HelloController.class);
    int counter = 0;
    private String keyCount = "count";
    private Long count = 0L;

    @Autowired
    private StringRedisTemplate template;
    @GetMapping("/helloworld")
    public String helloworld() {
        try {
            ValueOperations<String, String> ops = this.template.opsForValue();
            count = ops.increment(keyCount);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return count.toString();
    }

    @GetMapping("/hello")
    int hello() throws InterruptedException {
        Thread.sleep(200);
        ++counter;
        LOGGER.info("Counter: {}", counter);
        return counter;
    }

    //Uploading data from a file to a queue
    @GetMapping("/queue/files/{file_path}")
    public List<String> files(@PathVariable String file_path) {
        Jedis jedis  = new Jedis();
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(file_path))){
            String line;
            while ((line = br.readLine()) != null) {
                lines.add(line);
                jedis.lpush("queue#tasks", "firstTask");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            SECONDS.sleep(new Random().nextInt(1,10));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String task = jedis.rpop("queue#tasks");
        Job job = new BatchProperties.Job(lines);
        return job.getId();
    }

    //Processes data in the queue with a delay
    @GetMapping("/queue/process")
    public String process() {
        String job_id;
        job_id = req.queryParams("job_id");
        try {
            SECONDS.sleep(new Random().nextInt(1, 10));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        Job job = Job.return_value();
        Object result = job.return_value();
        return "{\"res\": " + result + "}";
    }

}

