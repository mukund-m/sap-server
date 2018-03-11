import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FieldOptionDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/field-option-definition/field-option-definition-detail.component';
import { FieldOptionDefinitionService } from '../../../../../../main/webapp/app/entities/field-option-definition/field-option-definition.service';
import { FieldOptionDefinition } from '../../../../../../main/webapp/app/entities/field-option-definition/field-option-definition.model';

describe('Component Tests', () => {

    describe('FieldOptionDefinition Management Detail Component', () => {
        let comp: FieldOptionDefinitionDetailComponent;
        let fixture: ComponentFixture<FieldOptionDefinitionDetailComponent>;
        let service: FieldOptionDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [FieldOptionDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FieldOptionDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(FieldOptionDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FieldOptionDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FieldOptionDefinitionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FieldOptionDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fieldOptionDefinition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
