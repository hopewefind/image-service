CREATE TABLE IF NOT EXISTS image
(
    id BIGSERIAL PRIMARY KEY,
    file_name TEXT NOT NULL,
    object_key TEXT NOT NULL,
    content_type TEXT NOT NULL,
    size_bytes BIGINT NOT NULL CHECK(size_bytes >= 0),
    uploaded_at TIMESTAMPTZ NOT NULL DEFAULT now()
);
CREATE INDEX IF NOT EXISTS idx_image_uploaded_at ON image (uploaded_at DESC);
CREATE UNIQUE INDEX IF NOT EXISTS ux_image_object_key ON image (object_key);
