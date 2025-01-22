CREATE TABLE IF NOT EXISTS "to_do" (
   "id"          bigserial PRIMARY KEY,
   "description" text NOT NULL,
   "done"        boolean DEFAULT FALSE,
   "due_date"    timestamp,
   "created_at"  timestamp
);
