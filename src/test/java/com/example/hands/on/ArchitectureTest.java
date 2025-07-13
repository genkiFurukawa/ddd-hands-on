package com.example.hands.on;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchRule;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchitectureTest {

    private final JavaClasses importedClasses = new ClassFileImporter()
            .importPackages("com.example.hands.on");

    @Test
    void ドメイン層はインフラストラクチャ層に依存してはいけない() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("..infrastructure..");

        rule.check(importedClasses);
    }

    @Test
    void ドメイン層はプレゼンテーション層に依存してはいけない() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..domain..")
                .should().dependOnClassesThat()
                .resideInAPackage("..presentation..");

        rule.check(importedClasses);
    }

    @Test
    void インフラストラクチャ層はプレゼンテーション層に依存してはいけない() {
        ArchRule rule = noClasses()
                .that().resideInAPackage("..infrastructure..")
                .should().dependOnClassesThat()
                .resideInAPackage("..presentation..");

        rule.check(importedClasses);
    }

    @Test
    void リポジトリ実装クラスはインフラストラクチャ層にあるべき() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("RepositoryImpl")
                .should().resideInAPackage("..infrastructure.repository..");

        rule.check(importedClasses);
    }

    @Test
    void コントローラクラスはプレゼンテーション層にあるべき() {
        ArchRule rule = classes()
                .that().haveSimpleNameEndingWith("Controller")
                .should().resideInAPackage("..presentation.controller..");

        rule.check(importedClasses);
    }
}