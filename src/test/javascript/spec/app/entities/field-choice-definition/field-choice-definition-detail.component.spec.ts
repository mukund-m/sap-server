import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FieldChoiceDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/field-choice-definition/field-choice-definition-detail.component';
import { FieldChoiceDefinitionService } from '../../../../../../main/webapp/app/entities/field-choice-definition/field-choice-definition.service';
import { FieldChoiceDefinition } from '../../../../../../main/webapp/app/entities/field-choice-definition/field-choice-definition.model';

describe('Component Tests', () => {

    describe('FieldChoiceDefinition Management Detail Component', () => {
        let comp: FieldChoiceDefinitionDetailComponent;
        let fixture: ComponentFixture<FieldChoiceDefinitionDetailComponent>;
        let service: FieldChoiceDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [FieldChoiceDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FieldChoiceDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(FieldChoiceDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FieldChoiceDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FieldChoiceDefinitionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FieldChoiceDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fieldChoiceDefinition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
