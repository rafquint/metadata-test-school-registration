package br.com.rqgr.infrastructure;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

/**
 * Utility class for ResponseEntity creation.
 * @see tech.jhipster.web.util.ResponseUtil
 */
public interface ResponseUtil {
    
    //TODO - javadoc
    static <X> ResponseEntity<List<X>> wrapOrNotFound(Page<X> maybeResponsePage) {
        final List<X> content = maybeResponsePage.getContent();
        //TODO - Use Monads insted this...
        if(content == null || content.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(maybeResponsePage);
        return ResponseEntity.ok().headers(headers).body(content);
    }

    /**
     * Wrap the optional into a {@link org.springframework.http.ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status, or if it's empty, it
     * returns a {@link org.springframework.http.ResponseEntity} with {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @return response containing {@code maybeResponse} if present or {@link org.springframework.http.HttpStatus#NOT_FOUND}
     */
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse) {
        return wrapOrNotFound(maybeResponse, null);
    }

    /**
     * Wrap the optional into a {@link org.springframework.http.ResponseEntity} with an {@link org.springframework.http.HttpStatus#OK} status with the headers, or if it's
     * empty, throws a {@link org.springframework.web.server.ResponseStatusException} with status {@link org.springframework.http.HttpStatus#NOT_FOUND}.
     *
     * @param <X>           type of the response
     * @param maybeResponse response to return if present
     * @param header        headers to be added to the response
     * @return response containing {@code maybeResponse} if present
     */
    static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> maybeResponse, HttpHeaders header) {
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    //TODO - javadoc
    static <X> ResponseEntity<X> wrapOrNotModified(Optional<X> maybeResponse){
        return wrapOrNotModified(maybeResponse, null);
    }
    
    //TODO - javadoc
    static <X> ResponseEntity<X> wrapOrNotModified(Optional<X> maybeResponse, HttpHeaders header){
        return maybeResponse.map(response -> ResponseEntity.ok().headers(header).body(response))
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_MODIFIED));
    }
}
