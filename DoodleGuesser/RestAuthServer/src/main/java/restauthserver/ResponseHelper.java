package restauthserver;

import restauthshared.dto.LoginResultDto;
import restauthshared.dto.RegistrationResultDto;
import fontysmultipurposelibrary.communication.rest.dto.BaseResultDto;
import fontysmultipurposelibrary.serialization.ISerializer;
import fontysmultipurposelibrary.serialization.SerializationProvider;

public class ResponseHelper {

    private ResponseHelper(){}

    public static String getErrorResponseString()
    {
        ISerializer<String> ser = SerializationProvider.getSerializer();
        BaseResultDto response = new BaseResultDto();
        response.setSuccess(false);
        return ser.serialize(response);
    }

    public static String getLoginResultDtoResponseString(String token)
    {
        ISerializer<String> ser = SerializationProvider.getSerializer();
        LoginResultDto response = new LoginResultDto();
        response.setSuccess(true);
        response.setToken(token);
        return ser.serialize(response);
    }

    public static String getRegistrationResultDtoResponseString(boolean success)
    {
        ISerializer<String> ser = SerializationProvider.getSerializer();
        RegistrationResultDto dto = new RegistrationResultDto();
        dto.setSuccess(success);
        return ser.serialize(dto);
    }

}
