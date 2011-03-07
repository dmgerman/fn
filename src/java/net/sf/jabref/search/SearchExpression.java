begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|/**  * SearchExpression.java  *  * @author Created by Omnicore CodeGuide  */
end_comment

begin_package
DECL|package|net.sf.jabref.search
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|search
package|;
end_package

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|StringReader
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Hashtable
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|regex
operator|.
name|PatternSyntaxException
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|BibtexEntry
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|JabRefPreferences
import|;
end_import

begin_import
import|import
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|SearchRule
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|RecognitionException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|TokenStreamException
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|AST
import|;
end_import

begin_class
DECL|class|SearchExpression
specifier|public
class|class
name|SearchExpression
implements|implements
name|SearchRule
block|{
DECL|field|treeParser
specifier|private
name|SearchExpressionTreeParser
name|treeParser
init|=
operator|new
name|SearchExpressionTreeParser
argument_list|()
decl_stmt|;
DECL|field|ast
specifier|private
name|AST
name|ast
init|=
literal|null
decl_stmt|;
DECL|field|prefs
specifier|private
name|JabRefPreferences
name|prefs
init|=
literal|null
decl_stmt|;
DECL|method|SearchExpression (JabRefPreferences prefs, Hashtable<String, String> searchOptions)
specifier|public
name|SearchExpression
parameter_list|(
name|JabRefPreferences
name|prefs
parameter_list|,
name|Hashtable
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchOptions
parameter_list|)
throws|throws
name|TokenStreamException
throws|,
name|RecognitionException
throws|,
name|PatternSyntaxException
block|{
name|this
operator|.
name|prefs
operator|=
name|prefs
expr_stmt|;
comment|// parse search expression
name|SearchExpressionParser
name|parser
init|=
operator|new
name|SearchExpressionParser
argument_list|(
operator|new
name|SearchExpressionLexer
argument_list|(
operator|new
name|StringReader
argument_list|(
name|searchOptions
operator|.
name|elements
argument_list|()
operator|.
name|nextElement
argument_list|()
argument_list|)
argument_list|)
argument_list|)
decl_stmt|;
comment|// supports only single entry
name|parser
operator|.
name|caseSensitive
operator|=
name|this
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"caseSensitiveSearch"
argument_list|)
expr_stmt|;
name|parser
operator|.
name|regex
operator|=
name|this
operator|.
name|prefs
operator|.
name|getBoolean
argument_list|(
literal|"regExpSearch"
argument_list|)
expr_stmt|;
name|parser
operator|.
name|searchExpression
argument_list|()
expr_stmt|;
comment|// this is the "global" rule
name|ast
operator|=
name|parser
operator|.
name|getAST
argument_list|()
expr_stmt|;
comment|// remember abstract syntax tree
block|}
DECL|method|applyRule (Map<String, String> searchStrings, BibtexEntry bibtexEntry)
specifier|public
name|int
name|applyRule
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchStrings
parameter_list|,
name|BibtexEntry
name|bibtexEntry
parameter_list|)
block|{
try|try
block|{
return|return
name|treeParser
operator|.
name|apply
argument_list|(
name|ast
argument_list|,
name|bibtexEntry
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RecognitionException
name|e
parameter_list|)
block|{
return|return
literal|0
return|;
comment|// this should never occur
block|}
block|}
DECL|method|validateSearchStrings (Map<String, String> searchStrings)
specifier|public
name|boolean
name|validateSearchStrings
parameter_list|(
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|searchStrings
parameter_list|)
block|{
return|return
literal|true
return|;
block|}
block|}
end_class

end_unit

