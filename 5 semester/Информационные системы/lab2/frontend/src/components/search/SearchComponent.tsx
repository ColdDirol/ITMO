import { Input, InputGroup } from "rsuite";
import SearchIcon from "@rsuite/icons/Search";

const styles = {
  marginBottom: 10,
};

const CustomInputGroupWidthButton = ({ placeholder, ...props }) => (
  <InputGroup {...props} inside style={styles}>
    <Input placeholder={placeholder} />
    <InputGroup.Button>
      <SearchIcon />
    </InputGroup.Button>
  </InputGroup>
);

export default function () {
  return (
    <>
      <CustomInputGroupWidthButton size="lg" placeholder="Large" />
    </>
  );
}
