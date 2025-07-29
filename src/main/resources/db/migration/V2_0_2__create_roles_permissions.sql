CREATE TABLE roles_permissions
(
    role_id         INT NOT NULL,
    permission_id   INT NOT NULL,
    CONSTRAINT roles_permissions_fk_role_id FOREIGN KEY (role_id) REFERENCES roles (id),
    CONSTRAINT roles_permissions_fk_permission_id FOREIGN KEY (permission_id) REFERENCES permissions (id)
);

CREATE INDEX roles_permissions_idx_role_id on roles_permissions (role_id);
CREATE INDEX roles_permissions_idx_permission_id on roles_permissions (permission_id);
