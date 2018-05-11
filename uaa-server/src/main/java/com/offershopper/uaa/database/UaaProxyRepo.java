package com.offershopper.uaa.database;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.offershopper.uaa.model.UaaModel;

public interface UaaProxyRepo extends MongoRepository<UaaModel, String> {

	public List<UaaModel> findByToken(String token);
}
