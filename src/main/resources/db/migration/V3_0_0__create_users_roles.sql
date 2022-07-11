CREATE TABLE users_roles
(
    user_id BIGINT NOT NULL,
    role_id INT    NOT NULL,
    CONSTRAINT users_roles_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (role_id),
    CONSTRAINT users_roles_fk_user_id FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE INDEX users_roles_idx_user_id on users_roles (user_id);
CREATE INDEX users_roles_idx_role_id on users_roles (role_id);
