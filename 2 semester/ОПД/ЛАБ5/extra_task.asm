ORG	0x0		; Инициализация векторов прерывания
V0:	WORD $DEFAULT, 0x180	; Вектор прерывания #0 
V1:	WORD $INT1, 0x180	; Вектор прерывания #1
V2:	WORD $DEFAULT, 0x180 	; Вектор прерывания #2
V3:	WORD $DEFAULT, 0x180	; Вектор прерывания #3
V4:	WORD $DEFAULT, 0x180	; Вектор прерывания #4
V5:	WORD $DEFAULT, 0x180	; Вектор прерывания #5
V6:	WORD $DEFAULT, 0x180	; Вектор прерывания #6
V7:	WORD $DEFAULT, 0x180	; Вектор прерывания #7

DEFAULT:	IRET	; Просто возврат
ORG	0x20   	; Загрузка начальных векторов прерывания
START:	DI	; Запрет прерывания
	CLA	; Очистка AC
	LD #9	; Разрешить прерывания и вектор №1
	OUT 0x19	; Разрешение прерывания для ВУ-8 (клавиатура)
	EI	; Разрешить прерывание

INT1:	
	SAVE:	WORD ?
	INDEX:	WORD ?

	READ:	DI
		CLA
		LD #0x00
		ST $INDEX
		CLA
		IN 0x019
		AND #0x40
		BEQ READ
		IN 0x018
		CMP #0x0A
		BEQ EXIT
		ST $SAVE

	WRITE:	CLA
		LD $SAVE
		BEQ READ
		ASR
		ST $SAVE
		BHIS DO
		BLO NO
		JUMP WRITE

	DO:	DI
		CLA
		IN 0x15
		AND #0x40
		BEQ DO
		LD $INDEX
		INC
		OUT 0x14
		DEC
		ADD #0x10
		ST $INDEX
		JUMP WRITE

	NO:	DI
		CLA
		IN 0x15
		AND #0x40
		LD $INDEX
		OUT 0x14
		ADD #0x10
		ST $INDEX
		JUMP WRITE
EXIT:	HLT