import { Button } from "rsuite";
import './LandingPage.css';

const LandingPage = () => {
    return (
        <div style={{ position: "relative", zIndex: 0 }}>
            <div className="landing-fullscreen-bg" />

            <div className="content" style={{ position: "relative", textAlign: "center" }}>
                <h1>Ваши финансы на любой орбите!</h1>
                <p>Гарантируем надежность, доступ и скорость во всех уголках вселенной.</p>
                <div style={{ padding: "30px", textAlign: "center" }}>
                    <Button appearance="primary" style={{ marginRight: '10px' }}>
                        Открыть галактический счет
                    </Button>
                    <Button appearance="ghost">Узнать больше</Button>
                </div>
            </div>

            <div className="content" style={{ position: "relative", padding: "25px" }}>
                <h2 style={{ paddingLeft: "15px" }}>Почему выбирают нас?</h2>
                <div className="features" style={{ padding: "30px", textAlign: "center" }}>
                    <div className="feature">
                        <h3>Мгновенные переводы через световые годы</h3>
                        <p>Наша квантовая сеть соединяет звездные системы за секунды.</p>
                    </div>
                    <div className="feature">
                        <h3>Надежность на уровне титана</h3>
                        <p>Галактическое шифрование нового поколения защищает ваши средства.</p>
                    </div>
                    <div className="feature">
                        <h3>Космические кредиты</h3>
                        <p>Кредиты для колонизации планет с минимальными процентами.</p>
                    </div>
                </div>
            </div>

            <div className="content" style={{ position: "relative", padding: "25px" }}>
                <h2 style={{ paddingLeft: "15px" }}>Наши тарифы</h2>
                <div className="plans" style={{ padding: "30px", textAlign: "center" }}>
                    <div className="plan">
                        <h3>Галактический старт</h3>
                        <p>Бесплатное обслуживание для новых пользователей.</p>
                    </div>
                    <div className="plan">
                        <h3>Межпланетный стандарт</h3>
                        <p>Оптимальное решение для постоянных клиентов.</p>
                    </div>
                    <div className="plan">
                        <h3>Звездный премиум</h3>
                        <p>Персональные менеджеры и привилегии на всех станциях.</p>
                    </div>
                </div>
            </div>

            <div className="content" style={{position: "relative", padding: "25px"}}>
                <h2 style={{ paddingLeft: "15px" }}>Отзывы наших клиентов</h2>
                <div className="testimonials" style={{padding: "30px", textAlign: "center"}}>
                    <blockquote>
                        <p>"Теперь я могу переводить средства детям на Титане за секунды."</p>
                        <footer>— Житель Альфа Центавры</footer>
                    </blockquote>
                    <blockquote>
                        <p>"Мне даже в черных дырах доступен мой баланс."</p>
                        <footer>— Пилот космического корабля</footer>
                    </blockquote>
                    <blockquote>
                        <p>"Галактический банк помог мне построить новый дом."</p>
                        <footer>— Колонизатор Марса</footer>
                    </blockquote>
                </div>
            </div>

        {/*    <div className="content" style={{position: "relative", padding: "25px"}}>*/}
        {/*        <h2 style={{ paddingLeft: "15px" }}>Свяжитесь с нами</h2>*/}
        {/*        <Form fluid style={{padding: "30px", textAlign: "center"}}>*/}
        {/*            <FormGroup>*/}
        {/*                <FormControlLabel>Ваше имя</FormControlLabel>*/}
        {/*                <FormControl name="name" type="text" placeholder="Введите ваше имя"/>*/}
        {/*            </FormGroup>*/}
        {/*            <FormGroup>*/}
        {/*                <FormControlLabel>Ваш email</FormControlLabel>*/}
        {/*                <FormControl name="email" type="email" placeholder="Введите ваш email"/>*/}
        {/*            </FormGroup>*/}
        {/*            <FormGroup>*/}
        {/*            <FormControlLabel>Ваше сообщение</FormControlLabel>*/}
        {/*                <FormControl*/}
        {/*                    name="message"*/}
        {/*                    componentClass="textarea"*/}
        {/*                    rows={5}*/}
        {/*                    placeholder="Введите ваше сообщение"*/}
        {/*                />*/}
        {/*            </FormGroup>*/}
        {/*            <Button appearance="primary" type="submit">*/}
        {/*                Отправить*/}
        {/*            </Button>*/}
        {/*        </Form>*/}
        {/*    </div>*/}
        </div>
    );
}

export default LandingPage;