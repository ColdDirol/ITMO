ORG	0x0			; Инициализация векторов прерывания
V0:	WORD $DEFAULT, 0x180	; Вектор прерывания #0 
V1:	WORD $DEFAULT, 0x180	; Вектор прерывания #1
V2:	WORD $INT2, 0x180 	; Вектор прерывания #2
V3:	WORD $INT3, 0x180	; Вектор прерывания #3
V4:	WORD $DEFAULT, 0x180	; Вектор прерывания #4
V5:	WORD $DEFAULT, 0x180	; Вектор прерывания #5
V6:	WORD $DEFAULT, 0x180	; Вектор прерывания #6
V7:	WORD $DEFAULT, 0x180	; Вектор прерывания #7

ORG	0x24   		; Загрузка начальных векторов прерывания
X:	WORD 0x0025
VAL1:	WORD 0x0013
VAL2:	WORD 0		; Получается, ячейка для сохранения DR от ВУ-2
MIN_BRACKET:	WORD 0xFFE6
MAX_BRACKET:	WORD 0x0018

DEFAULT:	IRET	; Просто возврат

START:	DI		; Запрет прерывания
	CLA		; Очистка AC
	OUT 5
	OUT 7
	LD #0xA		; Загрузить в AC инфу для MR КВУ-2
	OUT 5		; Загрузить AC в MR КВУ-2
	LD #0xB		; Загрузить в AC инфу для MR КВУ-3
	OUT 7		; Загрузить AC в MR КВУ-3

PROGRAM:	DI
	LD (X)
	DEC
	DEC
	CALL CHECK
	ST (X)
	EI
	JUMP PROGRAM

INT2:	; Для ВУ-2
	HLT
	IN 4
	ST $VAL2
	ASL
	ADD $VAL2
	ADD (X)
	ST (X)
	HLT
	IRET

INT3:	; Для ВУ-3
	CLA
	LD (X)	; X
	HLT
	ASL	; 2X
	ASL	; 4X
	ADD (X)	; 5X
	ADD #0x06	; 5X + 6
	OUT 6
	HLT
	IRET

CHECK:	; Проверка на ОДЗ
	CHECK_MIN:
		CMP $MIN_BRACKET
		BPL CHECK_MAX
		JUMP LOAD_MAX
	CHECK_MAX:
		CMP $MAX_BRACKET
		BMI RETURN
	LOAD_MAX:
		LD $MAX_BRACKET

RETURN:	RET