package catalog.domain;

import java.util.Objects;

import catalog.domain.validation.ValidationHandler;

public abstract class Entity<ID extends Identifier> {

    protected final ID id;

    public Entity(final ID id) {
        Objects.requireNonNull(id, "ID cannot be null");
        this.id = id;
    }

    public abstract void validate(ValidationHandler handler);

    public ID getId() {
        return id;
    }

    // Verifica a referencia do THIS é a mesma que tá indo no equals
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Entity<?> entity = (Entity<?>) o;
        return getId().equals(entity.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

}
