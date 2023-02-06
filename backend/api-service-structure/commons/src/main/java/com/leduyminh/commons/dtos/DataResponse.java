package com.leduyminh.commons.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;

@JsonIgnoreProperties("hibernateLazyInitializer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DataResponse implements Serializable {
    private Integer code;
    private Object result;
    private String message;
    private String description;

    private DataResponse(Builder builder) {
        this.code = builder.code;
        this.result = builder.result;
        this.message = builder.message;
        this.description = builder.description;
    }

    public static Builder ok() {
        return withCode(HttpStatus.OK.value());
    }

    public static Builder withCode(Integer code) {
        return new Builder(code);
    }

    @Override
    public String toString() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", new Locale("vi"));
            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            mapper.setDateFormat(dateFormat);
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static class Builder {
        private final Integer code; // This is important, so we'll pass it to the constructor.
        private Object result;
        private String message;
        private String description;

        public Builder(Integer code) {
            this.code = code;
        }

        public Builder withResult(Object result) {
            this.result = result;
            return this;
        }

        public Builder withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder withDescription(String description) {
            this.description = description;
            return this;
        }

        public DataResponse build() {
            DataResponse account = new DataResponse(this);
            return account;
        }
    }
}
