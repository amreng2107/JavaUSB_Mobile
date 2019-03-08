package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.ValidationError;
import java.util.Optional;

interface Validator <K> {

    Optional<ValidationError> validate(K k);

}
