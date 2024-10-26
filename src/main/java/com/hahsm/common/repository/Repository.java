package com.hahsm.common.repository;

import java.util.List;
import java.util.Optional;

public interface Repository<ModelType, IDType> {
   public List<ModelType> getAll(); 
   public Optional<ModelType> getByID(IDType id);

   public ModelType update(ModelType entity);

   public boolean delete(ModelType entity);
   public boolean deleteByID(IDType id);

}
