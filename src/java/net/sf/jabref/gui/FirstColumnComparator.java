begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|net.sf.jabref.gui
package|package
name|net
operator|.
name|sf
operator|.
name|jabref
operator|.
name|gui
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Comparator
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

begin_comment
comment|/**  * Created by IntelliJ IDEA.  * User: alver  * Date: Oct 14, 2005  * Time: 8:25:15 PM  * To change this template use File | Settings | File Templates.  */
end_comment

begin_class
DECL|class|FirstColumnComparator
specifier|public
class|class
name|FirstColumnComparator
implements|implements
name|Comparator
block|{
DECL|method|compare (Object o1, Object o2)
specifier|public
name|int
name|compare
parameter_list|(
name|Object
name|o1
parameter_list|,
name|Object
name|o2
parameter_list|)
block|{
name|BibtexEntry
name|e1
init|=
operator|(
name|BibtexEntry
operator|)
name|o1
decl_stmt|,
name|e2
init|=
operator|(
name|BibtexEntry
operator|)
name|o2
decl_stmt|;
name|int
name|score1
init|=
literal|0
decl_stmt|,
name|score2
init|=
literal|0
decl_stmt|;
comment|//if (Util.isMarked(e1))
comment|//    score1 -= 2;
comment|//if (Util.isMarked(e2))
comment|//    score2 -= 2;
if|if
condition|(
name|e1
operator|.
name|hasAllRequiredFields
argument_list|()
condition|)
name|score1
operator|++
expr_stmt|;
if|if
condition|(
name|e2
operator|.
name|hasAllRequiredFields
argument_list|()
condition|)
name|score2
operator|++
expr_stmt|;
return|return
name|score1
operator|-
name|score2
return|;
block|}
block|}
end_class

end_unit

