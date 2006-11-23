grammar Bst;

options {
    output=AST;
}

tokens {
	IDLIST;
	STACK;
	ENTRY;
	COMMANDS;
}

program : commands+ -> ^(COMMANDS commands+);

commands 
	: STRINGS^^ idList
	| INTEGERS^^ idList
	| FUNCTION^^ id stack
	| MACRO^^ id '{'! STRING '}'!
	| READ^^
	| EXECUTE^^ '{'! function '}'!
	| ITERATE^^ '{'! function '}'!
	| REVERSE^^ '{'! function '}'!
	| ENTRY^^ idList0 idList0 idList0
	| SORT^^;

identifier 
	: IDENTIFIER;

id 
	: '{'! identifier '}'!;
	
idList 
	: '{' identifier+ '}' -> ^(IDLIST identifier+);
	
idList0 
	: '{' identifier* '}' -> ^(IDLIST identifier*);

function 
	: '<' | '>' | '=' | '+' | '-' | ':=' | '*' | 'add.period$' | 'call.type$' | 'change.case$' | 'chr.to.int$'
	| 'cite$' | 'duplicat$' | 'empty$' | 'format.name$' | 'if$' | 'int.to.chr$' | 'int.to.str$' | 'missing$'
	| 'newline$' | 'num.names$' | 'pop$' | 'preamble$' | 'purify$' | 'quote$' | 'skip$' | 'stack$' | 'substring$'
	| 'swap$' | 'text.length$' | 'text.prefix$' | 'top$' | 'type$' | 'warning$' | 'while$' | 'width$'
	| 'write$' | identifier;
	
stack 
	: '{' stackitem+ '}' -> ^(STACK stackitem+);
	
stackitem
	: function
	| STRING 
	| INTEGER 
	| QUOTED
	| stack;

STRINGS : 'STRINGS';
INTEGERS : 'INTEGERS';
FUNCTION : 'FUNCTION';
EXECUTE : 'EXECUTE';
SORT : 'SORT';
ITERATE : 'ITERATE';
REVERSE : 'REVERSE';
ENTRY : 'ENTRY';
READ : 'READ';
MACRO : 'MACRO';

QUOTED
	: '\'' IDENTIFIER;

IDENTIFIER
	: LETTER (LETTER|NUMERAL)* ;

fragment LETTER
	: ('a'..'z'|'A'..'Z'|'.'|'$');

STRING
	: '"' (~('"'))* '"';
	
INTEGER   
	: '#' ('+'|'-')? NUMERAL+ ;

fragment NUMERAL
	: ('0'..'9');
	
WS
	: (' '|'\t'|'\n')+ {channel=99;} ;
	
LINE_COMMENT
    : '%' ~('\n'|'\r')* '\r'? '\n' {channel=99;}
    ;
	