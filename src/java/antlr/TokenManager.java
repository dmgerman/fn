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

begin_comment
comment|/** Interface that describes the set of defined tokens */
end_comment

begin_interface
DECL|interface|TokenManager
interface|interface
name|TokenManager
block|{
DECL|method|clone ()
specifier|public
name|Object
name|clone
parameter_list|()
function_decl|;
comment|/** define a token symbol */
DECL|method|define (TokenSymbol ts)
specifier|public
name|void
name|define
parameter_list|(
name|TokenSymbol
name|ts
parameter_list|)
function_decl|;
comment|/** Get the name of the token manager */
DECL|method|getName ()
specifier|public
name|String
name|getName
parameter_list|()
function_decl|;
comment|/** Get a token string by index */
DECL|method|getTokenStringAt (int idx)
specifier|public
name|String
name|getTokenStringAt
parameter_list|(
name|int
name|idx
parameter_list|)
function_decl|;
comment|/** Get the TokenSymbol for a string */
DECL|method|getTokenSymbol (String sym)
specifier|public
name|TokenSymbol
name|getTokenSymbol
parameter_list|(
name|String
name|sym
parameter_list|)
function_decl|;
DECL|method|getTokenSymbolAt (int idx)
specifier|public
name|TokenSymbol
name|getTokenSymbolAt
parameter_list|(
name|int
name|idx
parameter_list|)
function_decl|;
comment|/** Get an enumerator over the symbol table */
DECL|method|getTokenSymbolElements ()
specifier|public
name|Enumeration
name|getTokenSymbolElements
parameter_list|()
function_decl|;
DECL|method|getTokenSymbolKeys ()
specifier|public
name|Enumeration
name|getTokenSymbolKeys
parameter_list|()
function_decl|;
comment|/** Get the token vocabulary (read-only).      * @return A Vector of Strings indexed by token type */
DECL|method|getVocabulary ()
specifier|public
name|Vector
name|getVocabulary
parameter_list|()
function_decl|;
comment|/** Is this token manager read-only? */
DECL|method|isReadOnly ()
specifier|public
name|boolean
name|isReadOnly
parameter_list|()
function_decl|;
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
function_decl|;
comment|/** Get the highest token type in use */
DECL|method|maxTokenType ()
specifier|public
name|int
name|maxTokenType
parameter_list|()
function_decl|;
comment|/** Get the next unused token type */
DECL|method|nextTokenType ()
specifier|public
name|int
name|nextTokenType
parameter_list|()
function_decl|;
DECL|method|setName (String n)
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|n
parameter_list|)
function_decl|;
DECL|method|setReadOnly (boolean ro)
specifier|public
name|void
name|setReadOnly
parameter_list|(
name|boolean
name|ro
parameter_list|)
function_decl|;
comment|/** Is a token symbol defined? */
DECL|method|tokenDefined (String symbol)
specifier|public
name|boolean
name|tokenDefined
parameter_list|(
name|String
name|symbol
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

