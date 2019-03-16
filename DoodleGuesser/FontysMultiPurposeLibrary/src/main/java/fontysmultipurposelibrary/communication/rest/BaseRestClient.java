package fontysmultipurposelibrary.communication.rest;

import fontysmultipurposelibrary.communication.rest.dto.BaseRequestDto;
import fontysmultipurposelibrary.logging.Logger;
import fontysmultipurposelibrary.serialization.ISerializer;
import fontysmultipurposelibrary.serialization.SerializationProvider;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public abstract class BaseRestClient {

    ISerializer<String> serializer = SerializationProvider.getSerializer();

    public abstract String getBaseUr();

    public <T> T executeQueryGet(String queryGet, Class<T> clazz) {

        // Build the query for the REST service
        final String query = getBaseUr() + queryGet;
        // Perform the query
        HttpGet httpGet = new HttpGet(query);

        return executeRequest(httpGet, clazz);

    }

    private <T> T executeRequest(HttpUriRequest request, Class<T> clazz) {
        try(CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(request);
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            return (T) serializer.deserialize(entityString, clazz);
        } catch (IOException e) {
            return null;
        }
    }

    public <T> T executeQueryPost(BaseRequestDto request, String queryPost, Class<T> clazz) {

        // Build the query for the REST service
        final String query = getBaseUr() + queryPost;

        // Perform the query
        HttpPost httpPost = new HttpPost(query);
        httpPost.addHeader("content-type", MediaType.APPLICATION_JSON);
        //httpPost.

        StringEntity params;
        try {
            String json = serializer.serialize(request);
            params = new StringEntity(json);
            httpPost.setEntity(params);

        } catch (UnsupportedEncodingException ex) {
            Logger.getInstance().log(ex);
        }

        return executeRequest(httpPost, clazz);
    }

    public <T> T executeQueryPut(BaseRequestDto petRequest, String queryPut, Class<T> clazz) {

        // Build the query for the REST service
        final String query = getBaseUr() + queryPut;

        // Perform the query
        HttpPut httpPut = new HttpPut(query);
        httpPut.addHeader("content-type", "application/json");
        StringEntity params;
        try {
            params = new StringEntity(serializer.serialize(petRequest));
            httpPut.setEntity(params);
        } catch (UnsupportedEncodingException ex) {
            Logger.getInstance().log(ex);
        }

        return executeRequest(httpPut, clazz);
    }

    public <T> T executeQueryDelete(String queryDelete, Class<T> clazz) {

        // Build the query for the REST service
        final String query = getBaseUr() + queryDelete;

        // Perform the query
        HttpDelete httpDelete = new HttpDelete(query);

        return executeRequest(httpDelete, clazz);
    }
}
