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

begin_comment
comment|/**A GrammarAnalyzer computes lookahead from Grammar (which contains  * a grammar symbol table) and can then answer questions about the  * grammar.  *  * To access the RuleBlock for a rule name, the grammar symbol table  * is consulted.  *  * There should be no distinction between static& dynamic analysis.  * In other words, some of the easy analysis can be done statically  * and then the part that is hard statically can be deferred to  * parse-time.  Interestingly, computing LL(k) for k>1 lookahead  * statically is O(|T|^k) where T is the grammar vocabulary, but,  * is O(k) at run-time (ignoring the large constant associated with  * the size of the grammar).  In English, the difference can be  * described as "find the set of all possible k-sequences of input"  * versus "does this specific k-sequence match?".  */
end_comment

begin_interface
DECL|interface|GrammarAnalyzer
specifier|public
interface|interface
name|GrammarAnalyzer
block|{
comment|/**The epsilon token type is an imaginary type used  	 * during analysis.  It indicates an incomplete look() computation. 	 * Must be kept consistent with Token constants to be between 	 * MIN_USER_TYPE and INVALID_TYPE. 	 */
comment|// public static final int EPSILON_TYPE = 2;
DECL|field|NONDETERMINISTIC
specifier|public
specifier|static
specifier|final
name|int
name|NONDETERMINISTIC
init|=
name|Integer
operator|.
name|MAX_VALUE
decl_stmt|;
comment|// lookahead depth
DECL|field|LOOKAHEAD_DEPTH_INIT
specifier|public
specifier|static
specifier|final
name|int
name|LOOKAHEAD_DEPTH_INIT
init|=
operator|-
literal|1
decl_stmt|;
block|}
end_interface

end_unit

