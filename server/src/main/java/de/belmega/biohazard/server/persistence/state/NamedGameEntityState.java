package de.belmega.biohazard.server.persistence.state;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author tbelmega on 18.12.2016.
 */
public abstract class NamedGameEntityState {

    public abstract String getName();

    public abstract void setName(String name);


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("Name", getName())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NamedGameEntityState otherState = (NamedGameEntityState) o;

        return new EqualsBuilder()
                .append(this.getName(), otherState.getName())
                .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(this.getName())
                .build();
    }
}
