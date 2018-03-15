begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_comment
comment|// TODO: temporarily removed, due to split packages of ArchUnit
end_comment

begin_comment
comment|//package org.jabref.architecture;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import com.tngtech.archunit.junit.AnalyzeClasses;
end_comment

begin_comment
comment|//import com.tngtech.archunit.junit.ArchTest;
end_comment

begin_comment
comment|//import com.tngtech.archunit.junit.ArchUnitRunner;
end_comment

begin_comment
comment|//import com.tngtech.archunit.lang.ArchRule;
end_comment

begin_comment
comment|//import org.junit.runner.RunWith;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//@RunWith(ArchUnitRunner.class)
end_comment

begin_comment
comment|//@AnalyzeClasses(packages = "org.jabref")
end_comment

begin_comment
comment|//public class MainArchitectureTestsWithArchUnit {
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//    @ArchTest
end_comment

begin_comment
comment|//    public static final ArchRule doNotUseApacheCommonsLang3 =
end_comment

begin_comment
comment|//            noClasses().that().areNotAnnotatedWith(ApacheCommonsLang3Allowed.class)
end_comment

begin_comment
comment|//            .should().accessClassesThat().resideInAPackage("org.apache.commons.lang3");
end_comment

begin_comment
comment|//
end_comment

begin_comment
comment|//}
end_comment

end_unit

