package com.hahsm.common.repository;

import java.util.Optional;

import com.hahsm.datastructure.adt.List;

public interface Repository<ModelType, IDType> {
   public List<ModelType> getAll(); 
   public Optional<ModelType> getByID(IDType id);

   public boolean update(ModelType entity);

   public ModelType insert(ModelType newEntity);

   public boolean delete(ModelType entity);
   public boolean deleteByID(IDType id);

}
