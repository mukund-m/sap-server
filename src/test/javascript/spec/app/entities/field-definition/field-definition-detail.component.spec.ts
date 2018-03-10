import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FieldDefinitionDetailComponent } from '../../../../../../main/webapp/app/entities/field-definition/field-definition-detail.component';
import { FieldDefinitionService } from '../../../../../../main/webapp/app/entities/field-definition/field-definition.service';
import { FieldDefinition } from '../../../../../../main/webapp/app/entities/field-definition/field-definition.model';

describe('Component Tests', () => {

    describe('FieldDefinition Management Detail Component', () => {
        let comp: FieldDefinitionDetailComponent;
        let fixture: ComponentFixture<FieldDefinitionDetailComponent>;
        let service: FieldDefinitionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [FieldDefinitionDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FieldDefinitionService,
                    JhiEventManager
                ]
            }).overrideTemplate(FieldDefinitionDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FieldDefinitionDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FieldDefinitionService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new FieldDefinition(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.fieldDefinition).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
