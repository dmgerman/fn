begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/RIGHTS.html  *  * $Id$  */
end_comment

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|BitSet
import|;
end_import

begin_interface
DECL|interface|ANTLRGrammarParseBehavior
specifier|public
interface|interface
name|ANTLRGrammarParseBehavior
block|{
DECL|method|abortGrammar ()
specifier|public
name|void
name|abortGrammar
parameter_list|()
function_decl|;
DECL|method|beginAlt (boolean doAST_)
specifier|public
name|void
name|beginAlt
parameter_list|(
name|boolean
name|doAST_
parameter_list|)
function_decl|;
DECL|method|beginChildList ()
specifier|public
name|void
name|beginChildList
parameter_list|()
function_decl|;
comment|// Exception handling
DECL|method|beginExceptionGroup ()
specifier|public
name|void
name|beginExceptionGroup
parameter_list|()
function_decl|;
DECL|method|beginExceptionSpec (Token label)
specifier|public
name|void
name|beginExceptionSpec
parameter_list|(
name|Token
name|label
parameter_list|)
function_decl|;
DECL|method|beginSubRule (Token label, int line, boolean not)
specifier|public
name|void
name|beginSubRule
parameter_list|(
name|Token
name|label
parameter_list|,
name|int
name|line
parameter_list|,
name|boolean
name|not
parameter_list|)
function_decl|;
comment|// Trees
DECL|method|beginTree (int line)
specifier|public
name|void
name|beginTree
parameter_list|(
name|int
name|line
parameter_list|)
throws|throws
name|SemanticException
function_decl|;
DECL|method|defineRuleName (Token r, String access, boolean ruleAST, String docComment)
specifier|public
name|void
name|defineRuleName
parameter_list|(
name|Token
name|r
parameter_list|,
name|String
name|access
parameter_list|,
name|boolean
name|ruleAST
parameter_list|,
name|String
name|docComment
parameter_list|)
throws|throws
name|SemanticException
function_decl|;
DECL|method|defineToken (Token tokname, Token tokliteral)
specifier|public
name|void
name|defineToken
parameter_list|(
name|Token
name|tokname
parameter_list|,
name|Token
name|tokliteral
parameter_list|)
function_decl|;
DECL|method|endAlt ()
specifier|public
name|void
name|endAlt
parameter_list|()
function_decl|;
DECL|method|endChildList ()
specifier|public
name|void
name|endChildList
parameter_list|()
function_decl|;
DECL|method|endExceptionGroup ()
specifier|public
name|void
name|endExceptionGroup
parameter_list|()
function_decl|;
DECL|method|endExceptionSpec ()
specifier|public
name|void
name|endExceptionSpec
parameter_list|()
function_decl|;
DECL|method|endGrammar ()
specifier|public
name|void
name|endGrammar
parameter_list|()
function_decl|;
DECL|method|endOptions ()
specifier|public
name|void
name|endOptions
parameter_list|()
function_decl|;
DECL|method|endRule (String r)
specifier|public
name|void
name|endRule
parameter_list|(
name|String
name|r
parameter_list|)
function_decl|;
DECL|method|endSubRule ()
specifier|public
name|void
name|endSubRule
parameter_list|()
function_decl|;
DECL|method|endTree ()
specifier|public
name|void
name|endTree
parameter_list|()
function_decl|;
DECL|method|hasError ()
specifier|public
name|void
name|hasError
parameter_list|()
function_decl|;
DECL|method|noASTSubRule ()
specifier|public
name|void
name|noASTSubRule
parameter_list|()
function_decl|;
DECL|method|oneOrMoreSubRule ()
specifier|public
name|void
name|oneOrMoreSubRule
parameter_list|()
function_decl|;
DECL|method|optionalSubRule ()
specifier|public
name|void
name|optionalSubRule
parameter_list|()
function_decl|;
DECL|method|refAction (Token action)
specifier|public
name|void
name|refAction
parameter_list|(
name|Token
name|action
parameter_list|)
function_decl|;
DECL|method|refArgAction (Token action)
specifier|public
name|void
name|refArgAction
parameter_list|(
name|Token
name|action
parameter_list|)
function_decl|;
DECL|method|setUserExceptions (String thr)
specifier|public
name|void
name|setUserExceptions
parameter_list|(
name|String
name|thr
parameter_list|)
function_decl|;
DECL|method|refCharLiteral (Token lit, Token label, boolean inverted, int autoGenType, boolean lastInRule)
specifier|public
name|void
name|refCharLiteral
parameter_list|(
name|Token
name|lit
parameter_list|,
name|Token
name|label
parameter_list|,
name|boolean
name|inverted
parameter_list|,
name|int
name|autoGenType
parameter_list|,
name|boolean
name|lastInRule
parameter_list|)
function_decl|;
DECL|method|refCharRange (Token t1, Token t2, Token label, int autoGenType, boolean lastInRule)
specifier|public
name|void
name|refCharRange
parameter_list|(
name|Token
name|t1
parameter_list|,
name|Token
name|t2
parameter_list|,
name|Token
name|label
parameter_list|,
name|int
name|autoGenType
parameter_list|,
name|boolean
name|lastInRule
parameter_list|)
function_decl|;
DECL|method|refElementOption (Token option, Token value)
specifier|public
name|void
name|refElementOption
parameter_list|(
name|Token
name|option
parameter_list|,
name|Token
name|value
parameter_list|)
function_decl|;
DECL|method|refTokensSpecElementOption (Token tok, Token option, Token value)
specifier|public
name|void
name|refTokensSpecElementOption
parameter_list|(
name|Token
name|tok
parameter_list|,
name|Token
name|option
parameter_list|,
name|Token
name|value
parameter_list|)
function_decl|;
DECL|method|refExceptionHandler (Token exTypeAndName, Token action)
specifier|public
name|void
name|refExceptionHandler
parameter_list|(
name|Token
name|exTypeAndName
parameter_list|,
name|Token
name|action
parameter_list|)
function_decl|;
DECL|method|refHeaderAction (Token name,Token act)
specifier|public
name|void
name|refHeaderAction
parameter_list|(
name|Token
name|name
parameter_list|,
name|Token
name|act
parameter_list|)
function_decl|;
DECL|method|refInitAction (Token action)
specifier|public
name|void
name|refInitAction
parameter_list|(
name|Token
name|action
parameter_list|)
function_decl|;
DECL|method|refMemberAction (Token act)
specifier|public
name|void
name|refMemberAction
parameter_list|(
name|Token
name|act
parameter_list|)
function_decl|;
DECL|method|refPreambleAction (Token act)
specifier|public
name|void
name|refPreambleAction
parameter_list|(
name|Token
name|act
parameter_list|)
function_decl|;
DECL|method|refReturnAction (Token returnAction)
specifier|public
name|void
name|refReturnAction
parameter_list|(
name|Token
name|returnAction
parameter_list|)
function_decl|;
DECL|method|refRule (Token idAssign, Token r, Token label, Token arg, int autoGenType)
specifier|public
name|void
name|refRule
parameter_list|(
name|Token
name|idAssign
parameter_list|,
name|Token
name|r
parameter_list|,
name|Token
name|label
parameter_list|,
name|Token
name|arg
parameter_list|,
name|int
name|autoGenType
parameter_list|)
function_decl|;
DECL|method|refSemPred (Token pred)
specifier|public
name|void
name|refSemPred
parameter_list|(
name|Token
name|pred
parameter_list|)
function_decl|;
DECL|method|refStringLiteral (Token lit, Token label, int autoGenType, boolean lastInRule)
specifier|public
name|void
name|refStringLiteral
parameter_list|(
name|Token
name|lit
parameter_list|,
name|Token
name|label
parameter_list|,
name|int
name|autoGenType
parameter_list|,
name|boolean
name|lastInRule
parameter_list|)
function_decl|;
DECL|method|refToken (Token assignId, Token t, Token label, Token args, boolean inverted, int autoGenType, boolean lastInRule)
specifier|public
name|void
name|refToken
parameter_list|(
name|Token
name|assignId
parameter_list|,
name|Token
name|t
parameter_list|,
name|Token
name|label
parameter_list|,
name|Token
name|args
parameter_list|,
name|boolean
name|inverted
parameter_list|,
name|int
name|autoGenType
parameter_list|,
name|boolean
name|lastInRule
parameter_list|)
function_decl|;
DECL|method|refTokenRange (Token t1, Token t2, Token label, int autoGenType, boolean lastInRule)
specifier|public
name|void
name|refTokenRange
parameter_list|(
name|Token
name|t1
parameter_list|,
name|Token
name|t2
parameter_list|,
name|Token
name|label
parameter_list|,
name|int
name|autoGenType
parameter_list|,
name|boolean
name|lastInRule
parameter_list|)
function_decl|;
comment|// Tree specifiers
DECL|method|refTreeSpecifier (Token treeSpec)
specifier|public
name|void
name|refTreeSpecifier
parameter_list|(
name|Token
name|treeSpec
parameter_list|)
function_decl|;
DECL|method|refWildcard (Token t, Token label, int autoGenType)
specifier|public
name|void
name|refWildcard
parameter_list|(
name|Token
name|t
parameter_list|,
name|Token
name|label
parameter_list|,
name|int
name|autoGenType
parameter_list|)
function_decl|;
DECL|method|setArgOfRuleRef (Token argaction)
specifier|public
name|void
name|setArgOfRuleRef
parameter_list|(
name|Token
name|argaction
parameter_list|)
function_decl|;
DECL|method|setCharVocabulary (BitSet b)
specifier|public
name|void
name|setCharVocabulary
parameter_list|(
name|BitSet
name|b
parameter_list|)
function_decl|;
comment|// Options
DECL|method|setFileOption (Token key, Token value, String filename)
specifier|public
name|void
name|setFileOption
parameter_list|(
name|Token
name|key
parameter_list|,
name|Token
name|value
parameter_list|,
name|String
name|filename
parameter_list|)
function_decl|;
DECL|method|setGrammarOption (Token key, Token value)
specifier|public
name|void
name|setGrammarOption
parameter_list|(
name|Token
name|key
parameter_list|,
name|Token
name|value
parameter_list|)
function_decl|;
DECL|method|setRuleOption (Token key, Token value)
specifier|public
name|void
name|setRuleOption
parameter_list|(
name|Token
name|key
parameter_list|,
name|Token
name|value
parameter_list|)
function_decl|;
DECL|method|setSubruleOption (Token key, Token value)
specifier|public
name|void
name|setSubruleOption
parameter_list|(
name|Token
name|key
parameter_list|,
name|Token
name|value
parameter_list|)
function_decl|;
DECL|method|startLexer (String file, Token name, String superClass, String doc)
specifier|public
name|void
name|startLexer
parameter_list|(
name|String
name|file
parameter_list|,
name|Token
name|name
parameter_list|,
name|String
name|superClass
parameter_list|,
name|String
name|doc
parameter_list|)
function_decl|;
comment|// Flow control for grammars
DECL|method|startParser (String file, Token name, String superClass, String doc)
specifier|public
name|void
name|startParser
parameter_list|(
name|String
name|file
parameter_list|,
name|Token
name|name
parameter_list|,
name|String
name|superClass
parameter_list|,
name|String
name|doc
parameter_list|)
function_decl|;
DECL|method|startTreeWalker (String file, Token name, String superClass, String doc)
specifier|public
name|void
name|startTreeWalker
parameter_list|(
name|String
name|file
parameter_list|,
name|Token
name|name
parameter_list|,
name|String
name|superClass
parameter_list|,
name|String
name|doc
parameter_list|)
function_decl|;
DECL|method|synPred ()
specifier|public
name|void
name|synPred
parameter_list|()
function_decl|;
DECL|method|zeroOrMoreSubRule ()
specifier|public
name|void
name|zeroOrMoreSubRule
parameter_list|()
function_decl|;
block|}
end_interface

end_unit

