package restauthserver;

import fontysmultipurposelibrary.logging.LogLevel;
import fontysmultipurposelibrary.logging.Logger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import restauthserver.dal.repositories.ITokenRepository;
import restauthserver.dal.repositories.IUserRepository;
import restauthserver.dal.repositories.TokenRepo;
import restauthserver.dal.repositories.UserRepo;
import restauthshared.dto.AuthRequestDto;
import fontysmultipurposelibrary.communication.rest.dto.BaseResultDto;
import fontysmultipurposelibrary.dataacces.datacontexts.IDataContext;
import fontysmultipurposelibrary.dataacces.datacontexts.MSSqlDataContext;
import restauthserver.dal.datamappers.DataMapperFactory;
import restauthshared.dto.LoginResultDto;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/auth")
@Api(value = "/auth", description = "Web Services to authenticate to DoodleGuesser")
public class AuthRestService {

    private IUserRepository userRepository;
    private ITokenRepository tokenRepository;

    public AuthRestService() {
        String connectionUrl = PropertyFileHelper.getDbConnectionString();
        DataMapperFactory factory = new DataMapperFactory();
        IDataContext dataContext = new MSSqlDataContext(factory, connectionUrl);
        userRepository = new UserRepo(dataContext);
        tokenRepository = new TokenRepo(dataContext);
    }

    @GET
    @Path("/get")
    public Response get() {
        return  Response.status(200).entity(new BaseResultDto()).build();
    }

    @POST
    @Path("/login")
    @ApiOperation(value = "Login and returns a token", notes = "Returns a Token when succesfully loged in", response = LoginResultDto.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(AuthRequestDto loginRequest) {
        if(loginRequest.getUsername() == null || loginRequest.getHashedPassword() == null)
        {
            return Response.status(400).entity(ResponseHelper.getErrorResponseString()).build();
        }

        String token = userRepository.login(tokenRepository, loginRequest.getUsername(), loginRequest.getHashedPassword());
        if(token.equals(""))
        {
            Logger.getInstance().log(loginRequest.getUsername() + " failed to login", LogLevel.INFORMATION);
            return Response.status(401).entity(ResponseHelper.getErrorResponseString()).build();
        }

        return  Response.status(200).entity(ResponseHelper.getLoginResultDtoResponseString(token)).build();

    }

    @POST
    @Path("/register")
    @ApiOperation(value = "Register new account", notes = "Returns true when register was succesfull", response = Boolean.class)
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response register(AuthRequestDto loginRequest) {
        if(loginRequest.getUsername() == null || loginRequest.getHashedPassword() == null)
        {
            return Response.status(400).entity(ResponseHelper.getErrorResponseString()).build();
        }

        boolean success = userRepository.register(loginRequest.getUsername(), loginRequest.getHashedPassword());

        return  Response.status(200).entity(ResponseHelper.getRegistrationResultDtoResponseString(success)).build();

    }
}
