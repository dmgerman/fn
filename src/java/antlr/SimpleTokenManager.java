begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|antlr
package|package
name|antlr
package|;
end_package

begin_comment
comment|/* ANTLR Translator Generator  * Project led by Terence Parr at http://www.jGuru.com  * Software rights: http://www.antlr.org/license.html  *  * $Id$  */
end_comment

begin_import
import|import
name|java
operator|.
name|io
operator|.
name|*
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
name|Enumeration
import|;
end_import

begin_import
import|import
name|antlr
operator|.
name|collections
operator|.
name|impl
operator|.
name|Vector
import|;
end_import

begin_class
DECL|class|SimpleTokenManager
class|class
name|SimpleTokenManager
implements|implements
name|TokenManager
implements|,
name|Cloneable
block|{
DECL|field|maxToken
specifier|protected
name|int
name|maxToken
init|=
name|Token
operator|.
name|MIN_USER_TYPE
decl_stmt|;
comment|// Token vocabulary is Vector of String's
DECL|field|vocabulary
specifier|protected
name|Vector
name|vocabulary
decl_stmt|;
comment|// Hash table is a mapping from Strings to TokenSymbol
DECL|field|table
specifier|private
name|Hashtable
name|table
decl_stmt|;
comment|// the ANTLR tool
DECL|field|antlrTool
specifier|protected
name|Tool
name|antlrTool
decl_stmt|;
comment|// Name of the token manager
DECL|field|name
specifier|protected
name|String
name|name
decl_stmt|;
DECL|field|readOnly
specifier|protected
name|boolean
name|readOnly
init|=
literal|false
decl_stmt|;
DECL|method|SimpleTokenManager (String name_, Tool tool_)
name|SimpleTokenManager
parameter_list|(
name|String
name|name_
parameter_list|,
name|Tool
name|tool_
parameter_list|)
block|{
name|antlrTool
operator|=
name|tool_
expr_stmt|;
name|name
operator|=
name|name_
expr_stmt|;
comment|// Don't make a bigger vector than we need, because it will show up in output sets.
name|vocabulary
operator|=
operator|new
name|Vector
argument_list|(
literal|1
argument_list|)
expr_stmt|;
name|table
operator|=
operator|new
name|Hashtable
argument_list|()
expr_stmt|;
comment|// define EOF symbol
name|TokenSymbol
name|ts
init|=
operator|new
name|TokenSymbol
argument_list|(
literal|"EOF"
argument_list|)
decl_stmt|;
name|ts
operator|.
name|setTokenType
argument_list|(
name|Token
operator|.
name|EOF_TYPE
argument_list|)
expr_stmt|;
name|define
argument_list|(
name|ts
argument_list|)
expr_stmt|;
comment|// define<null-tree-lookahead> but only in the vocabulary vector
name|vocabulary
operator|.
name|ensureCapacity
argument_list|(
name|Token
operator|.
name|NULL_TREE_LOOKAHEAD
argument_list|)
expr_stmt|;
name|vocabulary
operator|.
name|setElementAt
argument_list|(
literal|"NULL_TREE_LOOKAHEAD"
argument_list|,
name|Token
operator|.
name|NULL_TREE_LOOKAHEAD
argument_list|)
expr_stmt|;
block|}
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
block|{
name|SimpleTokenManager
name|tm
decl_stmt|;
try|try
block|{
name|tm
operator|=
operator|(
name|SimpleTokenManager
operator|)
name|super
operator|.
name|clone
argument_list|()
expr_stmt|;
name|tm
operator|.
name|vocabulary
operator|=
operator|(
name|Vector
operator|)
name|this
operator|.
name|vocabulary
operator|.
name|clone
argument_list|()
expr_stmt|;
name|tm
operator|.
name|table
operator|=
operator|(
name|Hashtable
operator|)
name|this
operator|.
name|table
operator|.
name|clone
argument_list|()
expr_stmt|;
name|tm
operator|.
name|maxToken
operator|=
name|this
operator|.
name|maxToken
expr_stmt|;
name|tm
operator|.
name|antlrTool
operator|=
name|this
operator|.
name|antlrTool
expr_stmt|;
name|tm
operator|.
name|name
operator|=
name|this
operator|.
name|name
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|CloneNotSupportedException
name|e
parameter_list|)
block|{
name|antlrTool
operator|.
name|panic
argument_list|(
literal|"cannot clone token manager"
argument_list|)
expr_stmt|;
return|return
literal|null
return|;
block|}
return|return
name|tm
return|;
block|}
comment|/** define a token */
DECL|method|define (TokenSymbol ts)
specifier|public
name|void
name|define
parameter_list|(
name|TokenSymbol
name|ts
parameter_list|)
block|{
comment|// Add the symbol to the vocabulary vector
name|vocabulary
operator|.
name|ensureCapacity
argument_list|(
name|ts
operator|.
name|getTokenType
argument_list|()
argument_list|)
expr_stmt|;
name|vocabulary
operator|.
name|setElementAt
argument_list|(
name|ts
operator|.
name|getId
argument_list|()
argument_list|,
name|ts
operator|.
name|getTokenType
argument_list|()
argument_list|)
expr_stmt|;
comment|// add the symbol to the hash table
name|mapToTokenSymbol
argument_list|(
name|ts
operator|.
name|getId
argument_list|()
argument_list|,
name|ts
argument_list|)
expr_stmt|;
block|}
comment|/** Simple token manager doesn't have a name -- must be set externally */
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
comment|/** Get a token symbol by index */
DECL|method|getTokenStringAt (int idx)
specifier|public
name|String
name|getTokenStringAt
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
return|return
operator|(
name|String
operator|)
name|vocabulary
operator|.
name|elementAt
argument_list|(
name|idx
argument_list|)
return|;
block|}
comment|/** Get the TokenSymbol for a string */
DECL|method|getTokenSymbol (String sym)
specifier|public
name|TokenSymbol
name|getTokenSymbol
parameter_list|(
name|String
name|sym
parameter_list|)
block|{
return|return
operator|(
name|TokenSymbol
operator|)
name|table
operator|.
name|get
argument_list|(
name|sym
argument_list|)
return|;
block|}
comment|/** Get a token symbol by index */
DECL|method|getTokenSymbolAt (int idx)
specifier|public
name|TokenSymbol
name|getTokenSymbolAt
parameter_list|(
name|int
name|idx
parameter_list|)
block|{
return|return
name|getTokenSymbol
argument_list|(
name|getTokenStringAt
argument_list|(
name|idx
argument_list|)
argument_list|)
return|;
block|}
comment|/** Get an enumerator over the symbol table */
DECL|method|getTokenSymbolElements ()
specifier|public
name|Enumeration
name|getTokenSymbolElements
parameter_list|()
block|{
return|return
name|table
operator|.
name|elements
argument_list|()
return|;
block|}
DECL|method|getTokenSymbolKeys ()
specifier|public
name|Enumeration
name|getTokenSymbolKeys
parameter_list|()
block|{
return|return
name|table
operator|.
name|keys
argument_list|()
return|;
block|}
comment|/** Get the token vocabulary (read-only).      * @return A Vector of TokenSymbol      */
DECL|method|getVocabulary ()
specifier|public
name|Vector
name|getVocabulary
parameter_list|()
block|{
return|return
name|vocabulary
return|;
block|}
comment|/** Simple token manager is not read-only */
DECL|method|isReadOnly ()
specifier|public
name|boolean
name|isReadOnly
parameter_list|()
block|{
return|return
literal|false
return|;
block|}
comment|/** Map a label or string to an existing token symbol */
DECL|method|mapToTokenSymbol (String name, TokenSymbol sym)
specifier|public
name|void
name|mapToTokenSymbol
parameter_list|(
name|String
name|name
parameter_list|,
name|TokenSymbol
name|sym
parameter_list|)
block|{
comment|// System.out.println("mapToTokenSymbol("+name+","+sym+")");
name|table
operator|.
name|put
argument_list|(
name|name
argument_list|,
name|sym
argument_list|)
expr_stmt|;
block|}
comment|/** Get the highest token type in use */
DECL|method|maxTokenType ()
specifier|public
name|int
name|maxTokenType
parameter_list|()
block|{
return|return
name|maxToken
operator|-
literal|1
return|;
block|}
comment|/** Get the next unused token type */
DECL|method|nextTokenType ()
specifier|public
name|int
name|nextTokenType
parameter_list|()
block|{
return|return
name|maxToken
operator|++
return|;
block|}
comment|/** Set the name of the token manager */
DECL|method|setName (String name_)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name_
parameter_list|)
block|{
name|name
operator|=
name|name_
expr_stmt|;
block|}
DECL|method|setReadOnly (boolean ro)
specifier|public
name|void
name|setReadOnly
parameter_list|(
name|boolean
name|ro
parameter_list|)
block|{
name|readOnly
operator|=
name|ro
expr_stmt|;
block|}
comment|/** Is a token symbol defined? */
DECL|method|tokenDefined (String symbol)
specifier|public
name|boolean
name|tokenDefined
parameter_list|(
name|String
name|symbol
parameter_list|)
block|{
return|return
name|table
operator|.
name|containsKey
argument_list|(
name|symbol
argument_list|)
return|;
block|}
block|}
end_class

end_unit

