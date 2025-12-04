import React, { useEffect, useState } from "react";
import { Box, TextField, Button, Avatar, Typography } from "@mui/material";
import axios from "axios";
import { CommentModel } from "../model/CommentModel";
import { EMPTY_FIELD, MAX_LENGTH_100, MAX_LENGTH_1000 } from "../helper";

export const Comments = (): JSX.Element => {
    const [comments, setComments] = useState<CommentModel[]>([]);
    const [author, setAuthor] = useState<string>("");
    const [comment, setComment] = useState<string>("");
    const [authorError, setAuthorError] = useState<string>("");
    const [commentError, setCommentError] = useState<string>("");

    useEffect(() => {
        const fetchComments = async () => {
            try {
                const response = await axios.get<CommentModel[]>("/allComments");
                setComments(response.data);
            } catch (error) {
                console.error("Error fetching comments:", error);
            }
        };

        fetchComments();

        const intervalId = setInterval(fetchComments, 1000);

        return () => clearInterval(intervalId);
    }, []);

    const validateFields = (): boolean => {
        let isValid = true;

        if (author.trim() === "") {
            setAuthorError(EMPTY_FIELD);
            isValid = false;
        } else if (author.length > 100) {
            setAuthorError(MAX_LENGTH_100);
            isValid = false;
        } else {
            setAuthorError("");
        }

        if (comment.trim() === "") {
            setCommentError(EMPTY_FIELD);
            isValid = false;
        } else if (comment.length > 1000) {
            setCommentError(MAX_LENGTH_1000);
            isValid = false;
        } else {
            setCommentError("");
        }

        return isValid;
    };

    const handleSubmit = async () => {
        if (validateFields()) {
            try {
                const newComment: CommentModel = {
                    author: author,
                    comment: comment
                };

                await axios.post("/addComment", newComment);

                setAuthor("");
                setComment("");
                setAuthorError("");
                setCommentError("");
            } catch (error) {
                console.error("Error adding comment:", error);
            }
        }
    };

    const getAvatarColor = (name: string): string => {
        const colors = ["#1976d2", "#4caf50", "#ff9800", "#f44336", "#9c27b0"];
        const charCode = name.charCodeAt(0);
        return colors[charCode % colors.length];
    };

    return (
        <Box sx={{ maxWidth: 1200, margin: "0 auto", padding: 2 }}>
            <Box sx={{
                border: "1px solid #ccc",
                borderRadius: 2,
                padding: 2,
                marginBottom: 2,
                minHeight: 200,
                maxHeight: 400,
                overflowY: "auto"
            }}>
                {comments.map((c, index) => (
                    <Box key={c.id || index} sx={{ display: "flex", marginBottom: 2, alignItems: "flex-start" }}>
                        <Avatar sx={{
                            bgcolor: getAvatarColor(c.author),
                            marginRight: 2,
                            width: 48,
                            height: 48
                        }}>
                            {c.author.charAt(0).toLowerCase()}
                        </Avatar>
                        <Box sx={{ flex: 1 }}>
                            <Typography variant="h6" sx={{ fontWeight: "bold", marginBottom: 0.5 }}>
                                {c.author}
                            </Typography>
                            <Typography variant="body1">
                                {c.comment}
                            </Typography>
                        </Box>
                    </Box>
                ))}
            </Box>

            <Box sx={{ marginBottom: 2 }}>
                <TextField
                    fullWidth
                    label="Author"
                    variant="outlined"
                    value={author}
                    onChange={(e) => setAuthor(e.target.value)}
                    error={!!authorError}
                    helperText={authorError}
                    inputProps={{ "data-testid": "author-field" }}
                    sx={{ marginBottom: 2 }}
                />
                <TextField
                    fullWidth
                    label="Comment"
                    variant="outlined"
                    multiline
                    rows={4}
                    value={comment}
                    onChange={(e) => setComment(e.target.value)}
                    error={!!commentError}
                    helperText={commentError}
                    inputProps={{ "data-testid": "comment-field" }}
                />
            </Box>

            <Box>
                <Button
                    fullWidth
                    variant="contained"
                    color="primary"
                    size="large"
                    onClick={handleSubmit}
                    data-testid="submit-button"
                >
                    SEND
                </Button>
            </Box>
        </Box>
    );
}