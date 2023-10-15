%define last 0
%macro colon 2
	%2: dq last
		db %1, 0
	%define last %2
%endmacro
