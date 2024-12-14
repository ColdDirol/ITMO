import { Toggle } from "rsuite";
import userStore from "../../store/UserStore";

export default function ThemeComponent() {
  const { toggleTheme, isDarkTheme } = userStore();

  const handleToggleChange = (value: boolean) => {
    toggleTheme(value); // Передаем новое значение в toggleTheme
  };

  return (
    <Toggle
      checkedChildren="Dark"
      unCheckedChildren="Light"
      checked={isDarkTheme} // Используем isDarkTheme для управления состоянием
      onChange={handleToggleChange} // Обработчик события
    />
  );
}
