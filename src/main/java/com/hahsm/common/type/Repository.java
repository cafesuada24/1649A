package com.hahsm.common.type;

import java.util.Optional;
import java.util.function.Predicate;

import com.hahsm.datastructure.adt.List;

public interface Repository<ModelType, IDType> extends Observable<ModelType> {
    public List<ModelType> getAll();

    public List<ModelType> getByFilter(Predicate<ModelType> filter);

    public Optional<ModelType> getByID(IDType id);

    public boolean update(ModelType entity);

    public ModelType insert(ModelType newEntity);

    public List<ModelType> insert(List<ModelType> entities);

    public boolean delete(ModelType entity);

    public boolean deleteByID(IDType id);
}
