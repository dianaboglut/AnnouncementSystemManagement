INSERT INTO users (id,username, password) VALUES
                                                 (1,'jane_doe', 'password123'),
                                                 (2,'event_coordinator', 'ec_pass'),
                                                 (3,'library_staff', 'ls_pass');

INSERT INTO user_roles (user_id, roles) VALUES
                                        (1, 'USER'),
                                        (2, 'USER'),
                                        (3, 'USER');

INSERT INTO announcement (id,title, content, start_date, end_date, published, owner_id) VALUES
                                                                                                  (1, 'Road Closure Notice', 'Main Street will be closed for maintenance. Expect delays and follow detour signs.', '2025-01-15T00:00:00', '2025-12-20T00:00:00', true, 1),
                                                                                                  (2, 'Community Cleanup Day', 'Join us for our annual cleanup event. Volunteers will meet at City Park at 9 AM.', '2025-02-05T00:00:00', '2025-12-06T00:00:00', true, 1),
                                                                                                  (3, 'New Library Hours', 'The public library is extending its weekend hours starting next month.', '2025-03-01T00:00:00', '2025-12-31T00:00:00', true, 3),
                                                                                                  (4, 'Water Service Interruption', 'Water supply will be temporarily unavailable due to pipe repairs in the downtown area.', '2025-01-25T00:00:00', '2025-12-26T00:00:00', true, 1),
                                                                                                  (5, 'Holiday Event', 'Come celebrate with us at the annual Winter Festival with music, food, and fun activities.', '2025-11-01T00:00:00', '2025-12-31T00:00:00', true, 2);

INSERT INTO topics (id, name) VALUES
                                  (1, 'Events'),
                                  (2, 'Maintenance'),
                                  (3, 'Safety'),
                                  (4, 'Education'),
                                  (5, 'Government'),
                                  (6, 'Community'),
                                  (7, 'Utilities'),
                                  (8, 'Sales'),
                                  (9, 'Construction'),
                                  (10, 'Holiday');

INSERT INTO announcement_topics (announcement_id, topic_id) VALUES
                                                                (1,6),
                                                                (2,6),
                                                                (3,5),
                                                                (4,7),
                                                                (5,10);

