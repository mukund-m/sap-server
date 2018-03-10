import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { ChangeAppServerTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { RequestTypeDefConfigDetailComponent } from '../../../../../../main/webapp/app/entities/request-type-def-config/request-type-def-config-detail.component';
import { RequestTypeDefConfigService } from '../../../../../../main/webapp/app/entities/request-type-def-config/request-type-def-config.service';
import { RequestTypeDefConfig } from '../../../../../../main/webapp/app/entities/request-type-def-config/request-type-def-config.model';

describe('Component Tests', () => {

    describe('RequestTypeDefConfig Management Detail Component', () => {
        let comp: RequestTypeDefConfigDetailComponent;
        let fixture: ComponentFixture<RequestTypeDefConfigDetailComponent>;
        let service: RequestTypeDefConfigService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ChangeAppServerTestModule],
                declarations: [RequestTypeDefConfigDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    RequestTypeDefConfigService,
                    JhiEventManager
                ]
            }).overrideTemplate(RequestTypeDefConfigDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(RequestTypeDefConfigDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RequestTypeDefConfigService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new RequestTypeDefConfig(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.requestTypeDefConfig).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
