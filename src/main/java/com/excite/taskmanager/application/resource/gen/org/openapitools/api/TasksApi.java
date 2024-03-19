/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.3.0).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package com.excite.taskmanager.application.resource.gen.org.openapitools.api;

import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPostBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskPutBody;
import com.excite.taskmanager.application.resource.gen.org.openapitools.model.TaskResponseBody;
import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-03-15T10:10:31.462474+09:00[Asia/Tokyo]")
@Validated
@Tag(name = "tasks", description = "the tasks API")
public interface TasksApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * POST /tasks : タスク作成
     *
     * @param taskPostBody  (required)
     * @return タスク作成成功 (status code 200)
     *         or タスク作成失敗 BAD REQUEST (status code 400)
     */
    @Operation(
        operationId = "createTask",
        summary = "タスク作成",
        responses = {
            @ApiResponse(responseCode = "200", description = "タスク作成成功"),
            @ApiResponse(responseCode = "400", description = "タスク作成失敗 BAD REQUEST")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/tasks",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> createTask(
        @Parameter(name = "TaskPostBody", description = "", required = true) @Valid @RequestBody TaskPostBody taskPostBody
    ) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * DELETE /tasks/{id} : タスク削除
     *
     * @param id タスクID (required)
     * @return タスク削除成功 (status code 200)
     *         or タスク削除失敗 指定したIDのタスクが見つかりませんでした。 (status code 404)
     */
    @Operation(
        operationId = "deleteTask",
        summary = "タスク削除",
        responses = {
            @ApiResponse(responseCode = "200", description = "タスク削除成功"),
            @ApiResponse(responseCode = "404", description = "タスク削除失敗 指定したIDのタスクが見つかりませんでした。")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/tasks/{id}"
    )
    
    default ResponseEntity<Void> deleteTask(
        @Parameter(name = "id", description = "タスクID", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /tasks/{id} : タスク取得
     *
     * @param id タスクID (required)
     * @return タスク取得成功 (status code 200)
     *         or タスク取得失敗 指定したIDのタスクが見つかりませんでした。 (status code 404)
     */
    @Operation(
        operationId = "getTaskByID",
        summary = "タスク取得",
        responses = {
            @ApiResponse(responseCode = "200", description = "タスク取得成功", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = TaskResponseBody.class))
            }),
            @ApiResponse(responseCode = "404", description = "タスク取得失敗 指定したIDのタスクが見つかりませんでした。")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/tasks/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<TaskResponseBody> getTaskByID(
        @Parameter(name = "id", description = "タスクID", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id
    ) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"description\" : \"description\", \"id\" : 0, \"title\" : \"title\", \"deadline\" : \"2000-01-23\", \"status\" : 6 }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /tasks : タスク一覧取得
     *
     * @return タスク一覧取得成功 (status code 200)
     */
    @Operation(
        operationId = "getTasks",
        summary = "タスク一覧取得",
        responses = {
            @ApiResponse(responseCode = "200", description = "タスク一覧取得成功", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = TaskResponseBody.class)))
            })
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/tasks",
        produces = { "application/json" }
    )
    
    default ResponseEntity<List<TaskResponseBody>> getTasks(
        
    ) throws Exception {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"description\" : \"description\", \"id\" : 0, \"title\" : \"title\", \"deadline\" : \"2000-01-23\", \"status\" : 6 }, { \"description\" : \"description\", \"id\" : 0, \"title\" : \"title\", \"deadline\" : \"2000-01-23\", \"status\" : 6 } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /tasks/{id} : タスク更新
     *
     * @param id タスクID (required)
     * @param taskPutBody  (required)
     * @return タスク更新成功 (status code 200)
     *         or タスク更新失敗 BAD REQUEST (status code 400)
     *         or タスク更新失敗 指定したIDのタスクが見つかりませんでした。 (status code 404)
     */
    @Operation(
        operationId = "updateTask",
        summary = "タスク更新",
        responses = {
            @ApiResponse(responseCode = "200", description = "タスク更新成功"),
            @ApiResponse(responseCode = "400", description = "タスク更新失敗 BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "タスク更新失敗 指定したIDのタスクが見つかりませんでした。")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/tasks/{id}",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> updateTask(
        @Parameter(name = "id", description = "タスクID", required = true, in = ParameterIn.PATH) @PathVariable("id") Integer id,
        @Parameter(name = "TaskPutBody", description = "", required = true) @Valid @RequestBody TaskPutBody taskPutBody
    ) throws Exception {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
