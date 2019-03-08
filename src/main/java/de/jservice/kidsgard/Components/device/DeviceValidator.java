package de.jservice.kidsgard.Components.device;

import de.jservice.kidsgard.Components.ValidationError;
import org.springframework.stereotype.Component;
import java.util.Optional;
import static de.jservice.kidsgard.Components.ConstMessagesEN.ValidationMessages.REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA;
import de.jservice.kidsgard.data.Devices;

@Component
public class DeviceValidator extends ValidationSupport implements Validator<Devices> {

    @Override
    public Optional<ValidationError> validate(Devices address) {
        if (isNullOrEmptyString(address.getName()) ||
                isNullOrEmptyString(address.getAccount().getLimits()+"") ||
                isNullOrEmptyString(address.getStatus().name()) 
               ) {
            return Optional.of(new ValidationError(REQUIRED_DATA_NOT_FILLED_OR_BAD_DATA));
        }
        return Optional.empty();
    }

}
