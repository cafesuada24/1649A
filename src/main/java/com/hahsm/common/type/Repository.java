package com.hahsm.common.type;

import java.util.Optional;

import com.hahsm.datastructure.adt.List;

public interface Repository<ModelType, IDType> {
    public List<ModelType> getAll();

    public Optional<ModelType> getByID(IDType id);

    public boolean update(ModelType entity);

    public ModelType insert(ModelType newEntity);

    public boolean delete(ModelType entity);

    public boolean deleteByID(IDType id);

    public void loadEntity(ModelType entity);
}
