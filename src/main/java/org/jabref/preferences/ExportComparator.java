begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
DECL|package|org.jabref.preferences
package|package
name|org
operator|.
name|jabref
operator|.
name|preferences
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
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_class
DECL|class|ExportComparator
specifier|public
class|class
name|ExportComparator
implements|implements
name|Comparator
argument_list|<
name|List
argument_list|<
name|String
argument_list|>
argument_list|>
block|{
annotation|@
name|Override
DECL|method|compare (List<String> s1, List<String> s2)
specifier|public
name|int
name|compare
parameter_list|(
name|List
argument_list|<
name|String
argument_list|>
name|s1
parameter_list|,
name|List
argument_list|<
name|String
argument_list|>
name|s2
parameter_list|)
block|{
return|return
name|s1
operator|.
name|get
argument_list|(
literal|0
argument_list|)
operator|.
name|compareTo
argument_list|(
name|s2
operator|.
name|get
argument_list|(
literal|0
argument_list|)
argument_list|)
return|;
block|}
block|}
end_class

end_unit

