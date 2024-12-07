import { Message, useToaster } from "rsuite";
import { useCallback } from "react";

const MessageComponent = () => {
  const toaster = useToaster();

  const showMessage = useCallback(
    (type, content) => {
      const message = (
        <Message showIcon type={type} closable>
          <strong>{type}!</strong> {content}
        </Message>
      );

      toaster.push(message, { placement: "topCenter", duration: 5000 });
    },
    [toaster]
  );

  return { showMessage };
};

export default MessageComponent;
