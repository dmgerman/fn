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
DECL|interface|ToolErrorHandler
interface|interface
name|ToolErrorHandler
block|{
comment|/** Issue a warning about ambiguity between a alternates      * @param blk  The block being analyzed      * @param lexicalAnalysis  true for lexical rule      * @param depth  The depth of the ambiguity      * @param sets  An array of bitsets containing the ambiguities      * @param altIdx1  The zero-based index of the first ambiguous alternative      * @param altIdx2  The zero-based index of the second ambiguous alternative      */
DECL|method|warnAltAmbiguity ( Grammar grammar, AlternativeBlock blk, boolean lexicalAnalysis, int depth, Lookahead[] sets, int altIdx1, int altIdx2 )
specifier|public
name|void
name|warnAltAmbiguity
parameter_list|(
name|Grammar
name|grammar
parameter_list|,
name|AlternativeBlock
name|blk
parameter_list|,
name|boolean
name|lexicalAnalysis
parameter_list|,
name|int
name|depth
parameter_list|,
name|Lookahead
index|[]
name|sets
parameter_list|,
name|int
name|altIdx1
parameter_list|,
name|int
name|altIdx2
parameter_list|)
function_decl|;
comment|/** Issue a warning about ambiguity between an alternate and exit path.      * @param blk  The block being analyzed      * @param lexicalAnalysis  true for lexical rule      * @param depth  The depth of the ambiguity      * @param sets  An array of bitsets containing the ambiguities      * @param altIdx  The zero-based index of the ambiguous alternative      */
DECL|method|warnAltExitAmbiguity ( Grammar grammar, BlockWithImpliedExitPath blk, boolean lexicalAnalysis, int depth, Lookahead[] sets, int altIdx )
specifier|public
name|void
name|warnAltExitAmbiguity
parameter_list|(
name|Grammar
name|grammar
parameter_list|,
name|BlockWithImpliedExitPath
name|blk
parameter_list|,
name|boolean
name|lexicalAnalysis
parameter_list|,
name|int
name|depth
parameter_list|,
name|Lookahead
index|[]
name|sets
parameter_list|,
name|int
name|altIdx
parameter_list|)
function_decl|;
block|}
end_interface

end_unit

