package io.hubbox.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author fatih
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Camera {
    private Long id;
    private String ip;
    private int port;
    private String username;
    private String password;
}
